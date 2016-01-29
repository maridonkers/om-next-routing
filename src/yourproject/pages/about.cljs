(ns yourproject.pages.about
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]

            [yourproject.util :as util]
            [yourproject.parsers.about :as about-parser]))

;; -----------------------------------
;; Om-next component for the about page.
(defui AboutPage
  static om/IQuery
  (query [this]
         [:title :count])
  Object
  (render [this]
          (let [props (om/props this)
                {:keys [title
                        count]} props]
            
            (dom/div nil
                     (dom/h2 nil title)
                     (dom/span nil (str "About (count): " count))
                     (dom/button #js {:type "button"
                                      :onClick (fn [e]
                                                 (om/transact! this
                                                               `[(about/increment ~{})]))}
                                 "Incr!")))))

(def about-page (om/factory AboutPage))
