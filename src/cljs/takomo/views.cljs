(ns takomo.views
  (:require [re-frame.core :as re-frame]
            [takomo.events :as events]
            [takomo.subs :as subs]))

(defn main-panel []
  (let [repositories @(re-frame/subscribe [:repositories])
        loading-repositories? (re-frame/subscribe [:loading-repositories?])]
    [:div
     [:h1 "Repositories"]
     (if @loading-repositories?
      [:p "Loading, please wait..."]
      [:div
        [:p "All done"]
        (for [r repositories]
          [:p {} (get r "name")])])]))
