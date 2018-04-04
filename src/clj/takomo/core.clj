(ns takomo.core
  (:require [clj-http.client :as client]
            [jsonista.core :as j]
            [org.httpkit.client :as http]
            [reitit.core :as r]
            [reitit.ring :as ring]
            [ring.adapter.jetty :as jetty]))

(def config (read-string (slurp "config.edn")))

(def api-key (:api-key config))

(def organisations (:organisations config))

(def api-url "https://api.github.com/orgs/")

(defn get-org-urls [org endpoint]
  (let [org-url (str api-url org "/repos")]
    (if endpoint
      (map #(str org-url "?page=" % "&access_token=" api-key) (range 1 (+ 1 (Integer/parseInt (last (clojure.string/split endpoint #"="))))))
      (list (str org-url "?access_token=" api-key)))))

(defn get-urls [orgs]
  (let [responses (map client/get (map #(str api-url % "/repos" "?access_token=" api-key) orgs))
        ends (map #(get-in % [:links :last :href]) responses)]
        (mapcat get-org-urls orgs ends)))

(defn get-repositories []
  (let [urls (get-urls organisations)
        promises (doall (map http/get urls))
        results (doall (map deref promises))]
    (j/write-value-as-string (flatten (mapv (comp j/read-value :body) results)))))

(defn handler [request]
  {:status 200
   :headers {"Access-Control-Allow-Origin" "*"
             "Access-Control-Allow-Methods" "GET"}
   :body (get-repositories)})

(def app
  (ring/ring-handler
    (ring/router
      ["/api/repositories" handler])
    (ring/create-default-handler)))

(defn -main []
  (jetty/run-jetty app {:port 8080}))
