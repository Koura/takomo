(ns takomo.views
  (:require [re-frame.core :as re-frame]
            [takomo.events :as events]
            [takomo.subs :as subs]
            [reagent.core :as reagent]
            [cljsjs.chartjs]))

(defn show-license-chart
  [repositories]
  (let [context (.getContext (.getElementById js/document "license-chartjs") "2d")
        license-freqs (->> repositories
                           (map #(get % "license"))
                           (map #(let [license-name (get % "name")] (if license-name license-name "Unknown")))
                           (frequencies)
                           (sort-by val)
                           (reverse))
        counts (vec (vals license-freqs))
        names (vec (keys license-freqs))
        chart-data {:type "bar"
                    :data {:labels names
                           :datasets [{:data counts
                                       :label "Number of licenses"
                                       :backgroundColor "#90EE90"}
                                      ;{some other data}
                                       ]}}]
    (js/Chart. context (clj->js chart-data))))

(defn main-panel []
  (let [repositories @(re-frame/subscribe [:repositories])
        loading-repositories? (re-frame/subscribe [:loading-repositories?])]
    [:div
     [:h1 "Repositories"]
     (if @loading-repositories?
      [:p "Loading, please wait..."]
      [:div
       [:div
        [reagent/create-class {:component-did-mount #(show-license-chart repositories)
                               :display-name "chartjs-component"
                               :reagent-render (fn []
                                                 [:canvas {:id "license-chartjs" :width "600" :height "200"}])}]]])]))
