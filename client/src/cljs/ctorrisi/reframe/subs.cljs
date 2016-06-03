(ns ctorrisi.reframe.subs
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require [re-frame.core :refer [register-sub]]))

(register-sub
 :active-panel
 (fn [db _]
   (reaction (:active-panel @db))))

(register-sub
  :click-count
 (fn [db]
   (reaction (:click-count @db))))

(register-sub
  :time
  (fn [db]
    (reaction (:time @db))))

(register-sub
  :people
  (fn [db]
    (reaction (:people @db))))