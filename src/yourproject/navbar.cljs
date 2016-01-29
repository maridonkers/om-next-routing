(ns yourproject.navbar
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]

            [yourproject.util :as util]
            [yourproject.state :as state]
            [yourproject.reconciler :refer [parser]]
            [yourproject.parsers.app :as app-parser]
            [yourproject.parsers.navbar :as navbar-parser]))

(defn login!
  "Login and update in app state."
  [this entity]
  {:pre [(not (nil? this)), (not (nil? entity))]
   #_:post #_[(not (nil?  %))]}

  (om/transact! this
                `[(app/login ~entity)
                  :app]))

(defn logout!
  "Logout and update in app state."
  [this entity]
  {:pre [(not (nil? this)), (not (nil? entity))]
   #_:post #_[(not (nil?  %))]}

  (om/transact! this
                `[(app/logout ~entity)
                  :app]))

(defn get-html
  "Returns HTML for the navbar."
  [this]

  (let [props (om/props this)
        {:keys [collapsed?
                logins
                app]} props
        {:keys [lc logged-in?]} app]

    (dom/div nil
             (dom/a
              #js {:href "#/"}
              "Home ")
                    
             (dom/a
              #js {:href "#/browse"}
              "Browse ")
             
             (dom/a
              #js {:href "#/about"}
              "About")
             
             (dom/a
              #js {:href "#"
                   :onClick #(let [entity {:logged-in? logged-in?}]
                               (om/transact! this
                                             `[(app/login ~entity)
                                               :app]))}
              " | LOGIN")

             (dom/a
              #js {:href "#"
                   :onClick #(let [entity {:logged-in? logged-in?}]
                               (om/transact! this
                                             `[(app/logout ~entity)
                                               :app]))}
              " LOGOUT"))))

;;-------------------
;; Om Next component.
(defui Navbar
  static om/IQuery
  (query [this]
         [:collapsed?
          '[:app _]])
  Object
  (render
   [this]
   (dom/div #js {:id "navbar"} (get-html this))))

(def navbar (om/factory Navbar))
