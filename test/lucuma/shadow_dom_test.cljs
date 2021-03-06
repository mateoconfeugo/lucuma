(ns lucuma.shadow-dom-test
  (:require [cemerick.cljs.test :as t :refer-macros [deftest is use-fixtures]]
            [lucuma.core :as l :refer-macros [defwebcomponent]]
            [lucuma.shadow-dom :as sd]))

(when (sd/supported?)
  (defwebcomponent test-shadow-dom-1
    :document "<div></div>"
    :requires-shadow-dom? true)

  (defwebcomponent test-shadow-dom-2
    :prototype :test-shadow-dom-1
    :document "<span></span>"
    :requires-shadow-dom? true)

  (deftest shadow-roots
    (is (empty? (sd/shadow-roots nil)))
    (is (empty? (sd/shadow-roots (.createElement js/document "div"))))
    (is (= 1 (count (sd/shadow-roots (.createElement js/document "test-shadow-dom-1")))))
    (is (= 2 (count (sd/shadow-roots (.createElement js/document "test-shadow-dom-2")))))))

(defn wrap-registration
  [f]
  (l/register test-shadow-dom-1)
  (l/register test-shadow-dom-2)

  (f))

(use-fixtures :once wrap-registration)
