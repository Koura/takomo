(ns takomo.core
  (:require [org.httpkit.client :as http]
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
      (map #(str org-url "?page=" %) (range 1 (+ 1 (Integer/parseInt (last (clojure.string/split endpoint #"="))))))
      (list org-url))))

(defn get-urls [orgs]
  (let [responses (map http/get (map #(str api-url % "/repos") organisations))
        ends (map #(get-in % [:links :last :href]) responses)]
        (mapcat get-org-urls orgs ends)))

(defn get-repositories []
  (let [urls (get-urls organisations)
        promises (doall (map http/get urls))
        results (doall (map deref promises))]
    results))

(defn handler [request]
  {:status 200
   :body (get-repositories)})

   (defn wrap [handler id]
    (fn [request]
      (update (handler request) :via (fnil conj '()) id)))

  (def app
    (ring/ring-handler
      (ring/router
        ["/api/repositories" handler])
      (ring/create-default-handler)))

(defn -main []
  (jetty/run-jetty app {:port 8080}))
