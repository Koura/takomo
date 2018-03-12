(ns takomo.events
  (:require [re-frame.core :as re-frame]
            [takomo.db :as db])
  (:require-macros [takomo.core :refer [slurp]]))

(def configuration (slurp "config.edn"))

(re-frame/reg-event-db
 ::initialize-db
 (fn  [_ _]
   db/default-db))
