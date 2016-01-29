(ns yourproject.routing
  (:require [goog.dom :as gdom]
            [om.next :as om]
            
            [secretary.core :as secretary :include-macros true]
            [goog.events :as events]
            [goog.history.EventType :as EventType]

            [yourproject.state :as state]
            [yourproject.util :as util]
            [yourproject.reconciler :refer [reconciler parser]]
            
            [yourproject.app :refer [App set-page!]])
  (:import goog.History))

;; -------
;; Routes.
;; Extend when pages added. Also see pages function in app namespace.
;;
(secretary/set-config! :prefix "#")

(secretary/defroute home-page "/" []
  (set-page! "/"))

(secretary/defroute browse-page "/browse" []
  (set-page! "/browse"))  

(secretary/defroute about-page "/about" []
  (set-page! "/about"))

;; --------
;; History.
;; must be called after routes have been defined.
(defn hook-browser-navigation! 
  "Connects browser navigation to secretary routing."
  []
  (doto (History.)
    (events/listen
     EventType/NAVIGATE
     (fn [event]
       (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

(defn om-next-root!
  "Sets Om Next root component."
  []
  (om/add-root! reconciler
                App (gdom/getElement "app")))

(defn restore-page!
  "Restores saved page (if any); otherwise home page."
  []

  ;;TODO restore saved page.
  (util/nav! "/"))
