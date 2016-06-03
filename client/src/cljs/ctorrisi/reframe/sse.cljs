(ns ctorrisi.reframe.sse
  (:require [re-frame.core :refer [dispatch]]
            [kvlt.core :refer [event-source!]]
            [cljs.core.async :refer [<!]])
  (:require-macros [cljs.core.async.macros :refer [go go-loop]]))

(defn time-event
  [event]
  (dispatch [:time-updated (get event :data)]))

(defn people-event
  [event]
  (dispatch [:people-updated (get event :data)]))

(defn run-time-updater
  [url event-types]
  (let [events (event-source! url event-types)]
    (go-loop
      [event (<! events)]
      (if-not (nil? event)
        (do
          (case (get event :type)
            :message (time-event event)
            :people (people-event event)
            (.log js/console (str "Unknown event: " event)))
          (recur (<! events)))
        (do
          (.log js/console "No more events. Event channel is closed.")
          (js/setTimeout (fn [] (run-time-updater url event-types)) 1000))))))


(defn run-sse
  []
  (run-time-updater "rest/events" {:events #{:message :people}}))
