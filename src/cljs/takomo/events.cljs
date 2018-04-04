(ns takomo.events
  (:require [ajax.core :as ajax]
            [re-frame.core :as re-frame]
            [day8.re-frame.http-fx]
            [takomo.db :as db]))

(re-frame/reg-event-db
 ::initialize-db
 (fn  [_ _]
   db/default-db))

(re-frame/reg-event-db
  :load-repositories
  (fn [db [_ repositories]]
    (prn repositories)
    (assoc db :repositories repositories
              :loading-repositories? false)))