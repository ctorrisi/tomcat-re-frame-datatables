(ns ctorrisi.reframe.handlers
  (:require [ctorrisi.reframe.db :as db]
            [ajax.core :refer [GET POST]]
            [re-frame.core :refer [register-handler dispatch subscribe]]))

(register-handler
 :initialize-db
 (fn [_ _]
   db/app-db))

(register-handler
 :set-active-panel
 (fn [db [_ active-panel]]
   (assoc db :active-panel active-panel)))

(register-handler
  :increment-number
  (fn
    [app-state _]
    (let [click-count (subscribe [:click-count])]
    (GET (str "/test/rest/hello/addOne/" @click-count)
          :handler #(dispatch [:increment-click-count-response %1])
          :error-handler #(dispatch [:bad-response %1])))
    app-state))

(register-handler
  :increment-click-count-response
  (fn
    [app-state [_ response]]
    (assoc-in app-state [:click-count] response)))

(register-handler
  :bad-response
  (fn
    [app-state [_ response]]
    (.error js/console (str "Error: " response))))

(register-handler
  :time-updated
  (fn
    [app-state [_ time]]
    (assoc-in app-state [:time] time)))

(register-handler
  :people-updated
  (fn
    [app-state [_ people]]
     (assoc-in app-state [:people] people)))