package com.jpndev.portfolio.ui.study



interface  Interface {
    fun funA()
    fun funB(){

    }
}

 interface InterfaceA {
    fun funA()
     fun funA2()
     {
         print("A")
     }
}

 interface InterfaceB {
    fun funB()
     fun funA2()
     {
         print("B")
     }
}

abstract class AbstractClass {
    abstract  fun funA()
    fun funB()
    {

    }
    abstract  fun funC()

}


 class ChildClass : Y(),InterfaceA,InterfaceB {
     override fun funA() {

     }

     override fun funA2() {

     }

     override fun funC() {

     }




 }

/*abstract class X : AbstractClass(){

}*/
open class Y : AbstractClass(){
    override fun funA() {
    }

    override fun funC() {
    }


}
open class Z : AbstractClass(){
    override fun funA() {
    }

    override fun funC() {
    }


}