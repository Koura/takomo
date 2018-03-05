(ns takomo.views
  (:require [re-frame.core :as re-frame]
            [takomo.subs :as subs]
            ))

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div "Hello from " @name]))
