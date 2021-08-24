package com.jpndev.portfolio.ui.study.classess

import android.widget.TextView



/*
  Open-open for extension
  default - final
 */

open class ClassA {

    var nameA1:String="A"
    private var nameA2:String="A2 private"
    protected open var nameA3:String="A3 protected"
    internal var nameA4:String="A4 internal"

    open fun printName(txv:TextView) {
        txv.setText(nameA1)
    }

    protected class Nested {
        public val e: Int = 5
    }
    public class Nested2 {
        public val e: Int = 5
    }
}

class ClassB : ClassA() {

    public var nameB:String="B"
    override  var nameA3 = ""

    override fun printName(txv:TextView) {
        txv.setText(nameB+" "+nameA4)
    }
}

class Unrelate(val obj:ClassA){
    fun print(txv:TextView) {
        obj.nameA1
        //obj.Nested2=
        //obj.
        // txv.setText(nameB+" "+nameA4)
    }
}
