(defproject takomo "0.1.0-SNAPSHOT"
  :dependencies [[clj-http "3.8.0"]
                 [cljs-ajax "0.7.3"]
                 [cljsjs/chartjs "2.7.0-0"]
                 [day8.re-frame/http-fx "0.1.4"]
                 [http-kit "2.2.0"]
                 [metosin/jsonista "0.1.0"]
                 [metosin/reitit "0.1.0"]
                 [org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.10.145"]
                 [reagent "0.7.0"]
                 [re-frame "0.10.5"]
                 [ring "1.6.3"]]

  :plugins [[lein-cljsbuild "1.1.5"]]

  :min-lein-version "2.5.3"

  :source-paths ["src/clj"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]
  :main takomo.core
  :figwheel {:css-dirs ["resources/public/css"]}

  :profiles
  {:dev
   {:dependencies [[binaryage/devtools "0.9.9"]
                   [re-frisk "0.5.3"]]

    :plugins      [[lein-figwheel "0.5.13"]]}}

  :cljsbuild
  {:builds
   [{:id           "dev"
     :source-paths ["src"]
     :figwheel     {:on-jsload "takomo.core/mount-root"}
     :compiler     {:main                 takomo.core
                    :output-to            "resources/public/js/compiled/app.js"
                    :output-dir           "resources/public/js/compiled/out"
                    :source-map true
                    :pretty-print true
                    :asset-path           "js/compiled/out"
                    :source-map-timestamp true
                    :preloads             [devtools.preload re-frisk.preload]
                    :external-config      {:devtools/config {:features-to-install :all}}}}

    {:id           "min"
     :source-paths ["src/cljs"]
     :compiler     {:main            takomo.core
                    :output-to       "resources/public/js/compiled/app.js"
                    :optimizations   :advanced
                    :closure-defines {goog.DEBUG false}
                    :pretty-print    false}}]})
