package com.hossamelsharkawy.simplecart.data.cart

import android.content.Context
import android.util.Log
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import com.hossamelsharkawy.simplecart.CartPreferences
import com.hossamelsharkawy.simplecart.data.entities.CartItem
import com.hossamelsharkawy.simplecart.domain.ICartDataSource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

class CartLocalDataSource(context: Context) : ICartDataSource {

    companion object {
        const val DATA_STORE_FILE_NAME = "user_prefs.pb"
    }

    private val Context.createDataStore: DataStore<CartPreferences> by dataStore(
        fileName = DATA_STORE_FILE_NAME,
        serializer = CartPreferencesSerializer
    )

    private val dataStore: DataStore<CartPreferences> = context.createDataStore

    override suspend fun saveCartItem(cartItems: CartItem): CartItem {
        dataStore.updateData { preferences ->
            preferences.toBuilder()
                .putCartItems(cartItems.itemId.toString(), cartItems.qty.toString())
                .setLastEditTime()
                .build()
        }
        return cartItems
    }

    override suspend fun removeCartItem(cartItem: CartItem): CartItem? {
        dataStore.updateData { preferences ->
            preferences.toBuilder()
                .removeCartItems(cartItem.itemId.toString())
                .setLastEditTime()
                .build()
        }
        return null
    }



    suspend fun clearCartItems() {
        dataStore.updateData { preferences ->
            preferences.toBuilder()
                .clearCartItems()
                .setLastEditTime(null)
                .build()
        }
    }

    override suspend fun removeAllCartItems() {
        clearCartItems()
    }
    override suspend fun getCartItemsList() =
        cartPreferences
            .firstOrNull()
            ?.cartItemsMap
            ?.map { CartItem(it.key.toInt(), it.value.toInt()) }

    override suspend fun getLastEditTime() = cartPreferences.firstOrNull()?.lastEditTime




    private val cartPreferences = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e("CartDataStore", "Error reading preferences.", it)
                emit(CartPreferences.getDefaultInstance())
            } else {
                throw it
            }
        }
}

private fun CartPreferences.Builder.setLastEditTime() = apply {
    lastEditTime = System.currentTimeMillis().toString()
}


object CartPreferencesSerializer : Serializer<CartPreferences> {
    override val defaultValue: CartPreferences = CartPreferences.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): CartPreferences {
        try {
            return CartPreferences.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(
        t: CartPreferences,
        output: OutputStream
    ) = t.writeTo(output)
}

