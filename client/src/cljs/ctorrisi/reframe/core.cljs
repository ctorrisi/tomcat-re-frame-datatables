(ns ctorrisi.reframe.core
  (:require [reagent.core :as reagent]
              [re-frame.core :as re-frame]
              [ctorrisi.reframe.handlers]
              [ctorrisi.reframe.sse :as sse]
              [ctorrisi.reframe.subs]
              [ctorrisi.reframe.routes :as routes]
              [ctorrisi.reframe.views :as views]
              [ctorrisi.reframe.config :as config]))

(when config/debug?
  (println "dev mode"))

(defn mount-root
  []
  (reagent/render
    [views/main-panel]
    (.getElementById js/document "app")))

(defn ^:export init []
  (routes/app-routes)
  (re-frame/dispatch-sync [:initialize-db])
  (sse/run-sse)
  (mount-root))
