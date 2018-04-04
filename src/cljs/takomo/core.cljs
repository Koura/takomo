(ns takomo.core
  (:require [ajax.core :refer [GET]]
            [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [takomo.events :as events]
            [takomo.views :as views]
            [takomo.config :as config]))


(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn- init-repositories []
  (GET "http://localhost:8080/api/repositories" {:handler #(re-frame/dispatch [:load-repositories %])
                                                 :response-format :transit
                                                 :keywords? true}))

(defn ^:export init []
  (re-frame/dispatch-sync [::events/initialize-db])
  (init-repositories)
  (dev-setup)
  (mount-root))
