package com.hossamelsharkawy.simplecart.app.doc

import android.app.Activity
import android.view.View
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hossamelsharkawy.base.ui.recycel.*
import com.hossamelsharkawy.simplecart.data.entities.Product
import org.json.JSONObject

data class Student(
    var name: String? = null,
    var age: Int? = null,
    var marks: Int? = null
)


fun student(student: Student.() -> Unit): Student = Student().apply(student)

val student = student {
    name = "MindOrks"
    age = 20
    marks = 30
}

class Json() : JSONObject() {

    constructor(json: Json.() -> Unit) : this() {
        this.json()
    }

    infix fun <T> String.to(value: T) {
        put(this, value)
    }
}

val json = Json {
    "name" to "MindOrks"
    "age" to 20
}
