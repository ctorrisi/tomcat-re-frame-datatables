(ns ctorrisi.reframe.views
    (:require [re-frame.core :refer [dispatch subscribe]]
              [re-com.core :as re-com]
              [ctorrisi.reframe.components :as comps]))

;; home

(def selected-names (atom #{}))

(defn title []
  (let [click-count (subscribe [:click-count])]
    (fn []
      [re-com/title
       :label (str "Ajax Clicks! " @click-count)
       :level :level1])))

(defn button []
  (fn []
    [re-com/button
     :label "Click!"
     :on-click #(dispatch [:increment-number])]))

(defn timer-text []
  (fn []
    (let [time (subscribe [:time])]
      [:div (str "Current time: " @time)])))

(defn people-table []
  [comps/data-table
   (subscribe [:people])
   [{:title "Name"
     :data "name"}
    {:title "Age"
     :data "age"}]
   "name"
   selected-names
   {:multi-select true
    :scrollY 300}])

(defn home-title []
  (fn []
    [re-com/v-box
     :height "100%"
     :children [[title] [button] [timer-text] [people-table]]]))

(defn link-to-about-page []
  [re-com/hyperlink-href
   :label "go to About Page"
   :href "#/about"])

(defn home-panel []
  [re-com/v-box
   :gap "1em"
   :children [[home-title] [link-to-about-page]]])

;; about

(defn about-title []
  [re-com/title
   :label "This is the About Page."
   :level :level1])

(defn link-to-home-page []
  [re-com/hyperlink-href
   :label "go to Home Page"
   :href "#/"])

(defn about-panel []
  [re-com/v-box
   :gap "1em"
   :children [[about-title] [link-to-home-page]]])

;; main

(defmulti panels identity)
(defmethod panels :home-panel [] [home-panel])
(defmethod panels :about-panel [] [about-panel])
(defmethod panels :default [] [:div])

(defn main-panel []
  (let [active-panel (subscribe [:active-panel])]
    (fn []
      [re-com/v-box
       :height "100%"
       :children [(panels @active-panel)]])))