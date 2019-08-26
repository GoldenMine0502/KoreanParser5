package kr.goldenmine.parser.predicate

import kr.goldenmine.objects.ObjectStorage
import kr.goldenmine.parser.VariableStorage

enum class ValueScope(val lambda: (String) -> Boolean) {
    VARIABLE({ VariableStorage.isVariable(it)}), INT({
        try {
            it.toLong()
            true
        } catch(ex: Exception) {
            false
        }
    }), REALNUM({
        try {
            it.toDouble()
            true
        } catch(ex: Exception) {
            false
        }
    }), STRING({
        true
    }), OBJECT( {
        var value = false

        ObjectStorage.INSTANCE.creators.forEach { it2->
            if(it == it2.value.regex) {
                value = true
                return@forEach
            }
        }

        value
    }), CONSTANT({ !VariableStorage.isVariable(it) }), ;


    // OBJECT STORAGE 개발
    // regex에 부합하는거 1개이상
}