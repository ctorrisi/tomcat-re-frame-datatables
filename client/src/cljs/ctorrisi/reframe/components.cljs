(ns ctorrisi.reframe.components
  (:require [reagent.core :as reagent]
            [goog.json :as json]
            [jayq.core :as jayq :refer [$]]))

(defn- table-striped-no-wrap
  []
  [:table.table.table-striped.table-bordered.nowrap {:cell-spacing 0}])

(defn- get-data-table
  [comp]
  (.DataTable ($ (reagent/dom-node comp))))

(defn- draw-data-table
  [comp]
  (let [[grid-data data] (reagent/children comp)
        data-table (get-data-table comp)]
    (reset! grid-data (json/parse data))
    (.clear data-table)
    (.draw (.rows.add data-table @grid-data))))

(def selected-prop "selected")

(defn- handle-row-select
  [dt e key selected multi-select]
  (let [row (.row dt (.-target e))
        id (str (get (js->clj (.data row)) key))
        node (.node row)]
    (if (true? multi-select)
      (if (= (count @selected) 0)
        (do (reset! selected (conj #{} id))
            (.removeClass (.to$ (.nodes (.rows dt ".selected"))) selected-prop)
            (.addClass ($ node) selected-prop))
        (if (.-ctrlKey e)
          (if (contains? @selected id)
            (do (reset! selected (disj @selected id))
                (.removeClass ($ node) selected-prop))
            (do (reset! selected (conj @selected id))
                (.addClass ($ node) selected-prop)))
          (do (reset! selected (conj #{} id))
              (.removeClass (.to$ (.nodes (.rows dt ".selected"))) selected-prop)
              (.addClass ($ node) selected-prop))))
      (if (and (contains? @selected id) (.-ctrlKey e))
        (do (reset! selected #{})
            (.removeClass (.to$ (.nodes (.rows dt ".selected"))) selected-prop))
        (do (reset! selected (conj #{} id))
            (.removeClass (.to$ (.nodes (.rows dt ".selected"))) selected-prop)
            (.addClass ($ node) selected-prop))))
    (.draw dt)))

(defn- data-grid-did-mount
  [comp]
  (let [comp-dom ($ (reagent/dom-node comp))
        [grid-data _ columns key selected multi-select scrollY] (reagent/children comp)
        scroll-position (atom nil)]
    (.DataTable comp-dom
                (clj->js
                  {:columns columns
                   :stateSave true
                   :data @grid-data
                   :scroller true
                   :deferRender true
                   :scrollY scrollY
                   :rowCallback (fn [row data _]
                                  (if (contains? @selected (str (get (js->clj data) key)))
                                    (.addClass ($ row) "selected")))
                   :preDrawCallback (fn [_]
                                      (reset! scroll-position (.scrollTop (.parent comp-dom))))
                   :drawCallback (fn [_]
                                   (.scrollTop (.parent comp-dom) @scroll-position))}))
    (let [dt (.DataTable comp-dom)]
      (jayq/on dt :click "tr" (fn [e]
                                (handle-row-select dt e key selected multi-select))))))

(defn- data-grid-inner []
  (reagent/create-class {:display-name "data-table"
                         :component-did-mount data-grid-did-mount
                         :component-did-update draw-data-table
                         :reagent-render table-striped-no-wrap}))

(defn data-table
  [subscription columns unique-key selected-items & [{:keys [grid-data multi-select scrollY]
                                                      :or {grid-data (atom [])
                                                           multi-select false
                                                           scrollY 300}}]]
  (fn []
    [data-grid-inner grid-data @subscription columns unique-key selected-items multi-select scrollY]))