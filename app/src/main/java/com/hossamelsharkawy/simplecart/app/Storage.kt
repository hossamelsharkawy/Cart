import android.content.Context
import android.content.SharedPreferences
import com.hossamelsharkawy.base.extension.add
import com.hossamelsharkawy.simplecart.app.App
import java.util.*

/**
 * Created by Hossam Elsharkawy
0201099197556
on 7/9/2018.  time :10:20

 */
object Storage {

    const val _FavList ="_FavList"
    const val _SearchKeysList ="_SearchKeysList"
    const val _lang ="_lang"

    var lang: String
        set(value) {
            sp.add(Pair(_lang, value))
        }
        get() = sp.getString(_lang, Locale.getDefault().language)!!


    val sp: SharedPreferences by lazy {
        App.get().getSharedPreferences("general", Context.MODE_PRIVATE)
    }

    var favList: String? = null
        get() {
            if (field == null) {
                field = sp.getString(_FavList, null)
            }
            return field
        }
        set(value) {
            sp.add(Pair(_FavList , value))
            field = value
        }


   var searchKeysList: String? = null
        get() {
            if (field == null) {
                field = sp.getString(_SearchKeysList, null)
            }
            return field
        }
        set(value) {
            sp.add(Pair(_SearchKeysList , value))
            field = value
        }

}