(ns takomo.subs
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
  :loading-repositories?
  (fn [db]
    (:loading-repositories? db)))

(re-frame/reg-sub
  :repositories
  (fn [db]
    (:repositories db)))