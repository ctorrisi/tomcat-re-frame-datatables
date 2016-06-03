(defproject reframe-client "0.1.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.14"]
                 [org.clojure/core.async "0.2.374"]
                 [io.nervous/kvlt "0.1.1"]
                 [reagent "0.6.0-alpha2"]
                 [re-frame "0.7.0"]
                 [re-com "0.8.3"]
                 [secretary "1.2.3"]
                 [cljs-ajax "0.5.5"]
                 [jayq "2.5.4"]]

  :min-lein-version "2.5.3"

  :source-paths ["src/clj"]

  :plugins [[lein-cljsbuild "1.1.3"]
            [lein-figwheel "0.5.0-6"]]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  :figwheel {:css-dirs ["resources/public/css"]}

  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src/cljs"]
                        :figwheel {:on-jsload "ctorrisi.reframe.core/mount-root"}
                        :compiler {:main ctorrisi.reframe.core
                                   :output-to "resources/public/js/compiled/app.js"
                                   :output-dir "resources/public/js/compiled/out"
                                   :asset-path "js/compiled/out"
                                   :source-map-timestamp true}}

                       {:id "min"
                        :source-paths ["src/cljs"]
                        :compiler {:main ctorrisi.reframe.core
                                   :output-to "resources/public/js/compiled/app.js"
                                   :optimizations :advanced
                                   :closure-defines {goog.DEBUG false}
                                   :closure-warnings {:externs-validation :off}
                                   :pretty-print false
                                   :externs ["resources/public/js/jquery-2.2.3.min.js"
                                             "resources/public/js/datatables.min.js"]}}]})
