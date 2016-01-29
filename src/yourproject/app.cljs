(ns yourproject.app
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [secretary.core :as secretary]

            [clojure.string :as s]
            [yourproject.util :as util]
            
            [yourproject.reconciler :refer [reconciler]]
            [yourproject.parsers.app :as app-parser]
            
            [yourproject.navbar :refer [Navbar navbar]]
            [yourproject.pages.home :refer [HomePage home-page]]
            [yourproject.pages.browse :refer [BrowsePage browse-page]]
            [yourproject.pages.about :refer [AboutPage about-page]]))

;;-----------
;; Constants.
(def pages
  "Information on pages. Update this when new pages are added. Also
  update the routes in the routing namespace. Beware: (a) keywords
  *must* start with the :page+ prefix. (b) the URL /home is an alias
  for / (under the hood) so don't define a /home entry."
  
  {"/" [:page+home
        (om/get-query HomePage)
        home-page]
   "/browse" [:page+browse
              (om/get-query BrowsePage)
              browse-page]
   "/about" [:page+about
             (om/get-query AboutPage)
             about-page]})

;;---------
;; Queries.
(def app-query
  "Application level query."
  {:app [:logged-in?]})

(def navbar-query
  "Navbar query"
  {:navbar (om/get-query Navbar)})

(defn page-query
  "Gets query by page keyword and page subquery."
  [kw sq]
  {kw sq})

(defn query-by-page
  "Gets query by page."
  [page]
  {:pre [(not (nil? page))]
   :post [(not (nil? %))]}

  (if (contains? pages page)
    (let [pi (get pages page)]
      [app-query
       navbar-query
       (page-query (first pi) (second pi))])
    ;; Forces post-assert fail.
    nil))

(defn page-info-by-props
  "Gets pages element for query via props."
  [props]
  {:pre [(not (nil? props))]
   :post [(not (nil? %))]}

  (let [kw (first (filter #(s/starts-with? (str %) ":page+")
                          (keys props)))]
    (if (not (nil? kw))
      ;; Here kw has a :page+ type keyword.
      (let [kws (str kw)
            pg (str "/" (subs kws 6))
            pgi (if (= pg "/home") "/" pg)]
        (get pages pgi))
      ;; Forces post-assert fail.
      nil)))

(defn keyword-by-props
  "Gets keyword for query via props."
  [props]
  {:pre [(not (nil? props))]
   :post [(not (nil? %))]}
  
  (let [pi (page-info-by-props props)]
    (if (not (nil? pi))
      (first pi)
      ;; Forces post-assert fail.
      nil)))

(defn factory-fcn-by-props
  "Gets create function for query via props."
  [props]
  {:pre [(not (nil? props))]
   :post [(not (nil? %))]}
  
  (let [pi (page-info-by-props props)]
    (if (not (nil? pi))
      (second (next pi))
      ;; forces post-assert fail
      nil)))

;;------------------------
;; Om-next root component.
;;
(defui App
  static om/IQuery
  (query [this]
         (query-by-page "/"))

  Object
  (render [this]
          
          (let [props (om/props this)
                app-props (:app props)
                {:keys [logged-in?]} app-props
                
                navbar-props (:navbar props)
                pkw (keyword-by-props props)
                page-props (pkw props)]

            (dom/div nil
                     (dom/h4 nil (str "*** "(if logged-in? "LOGGED IN" "LOGGED OUT") "***"))
                     
                     (navbar navbar-props)
                     ((factory-fcn-by-props props) page-props)))))

;;-----------------
;; Sets page.
;;
(defn set-page!
  "Sets page via an Om Next set-query call. The resulting re-render of
  App displays the new page."
  [page]
  {:pre [(not (nil? page))]}

  (let [root (om/app-root reconciler)]
    (when (and (not (nil? page))
               (not (nil? root)))
      (let [q (query-by-page page)]
        (om/set-query! root
                       {:query q})))))
