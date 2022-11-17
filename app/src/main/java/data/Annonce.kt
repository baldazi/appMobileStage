package data

import android.provider.ContactsContract
import java.time.Duration
import java.util.*

data class Annonce(var title:String,var location:String, var company:String, var description:String,
                   var duration: Int, var initDate:String, var email:String) {
}
