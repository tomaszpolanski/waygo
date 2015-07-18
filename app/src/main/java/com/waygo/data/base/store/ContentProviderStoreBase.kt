package com.waygo.data.base.store

import com.waygo.data.base.contract.DatabaseContract

import android.content.ContentResolver
import android.content.ContentValues
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.Handler
import android.os.HandlerThread
import android.util.Log

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

import rx.Observable
import rx.android.internal.Preconditions
import rx.subjects.PublishSubject
import rx.subjects.Subject

public abstract class ContentProviderStoreBase<T, U>(protected val contentResolver: ContentResolver, private val databaseContract: DatabaseContract<T>) {

    private val subjectMap = ConcurrentHashMap<Uri, Subject<T, T>>()

    init {
        Preconditions.checkNotNull(contentResolver, "Content Resolver cannot be null.")
        Preconditions.checkNotNull(databaseContract, "Database Contract cannot be null.")
        this.contentResolver.registerContentObserver(getContentUri(), true, getContentObserver())
    }

    private fun getContentObserver(): ContentObserver {
        return object : ContentObserver(createHandler(this.javaClass.getSimpleName())) {
            override fun onChange(selfChange: Boolean, uri: Uri) {
                super.onChange(selfChange, uri)
                Log.v(TAG, "onChange(" + uri + ")")

                if (subjectMap.containsKey(uri)) {
                    subjectMap.get(uri).onNext(query(uri))
                }
            }
        }
    }

    public fun put(item: T) {
        insertOrUpdate(item)
    }

    public fun getStream(id: U): Observable<T> {
        Log.v(TAG, "getStream(" + id + ")")
        val item = query(id)
        val observable = lazyGetSubject(id)
        if (item != null) {
            Log.v(TAG, "Found existing item for id=" + id)
            return observable.startWith(item)
        }
        return observable
    }

    private fun lazyGetSubject(id: U): Observable<T> {
        Log.v(TAG, "lazyGetSubject(" + id + ")")
        val uri = getUriForKey(id)
        subjectMap.putIfAbsent(uri, PublishSubject.create<T>())
        return subjectMap.get(uri)
    }

    public open fun insertOrUpdate(item: T) {
        Preconditions.checkNotNull(item, "Item cannot be null.")

        val uri = getUriForKey(getIdFor(item))
        Log.v(TAG, "insertOrUpdate to " + uri)
        val values = getContentValuesForItem(item)
        Log.v(TAG, "values(" + values + ")")
        if (contentResolver.update(uri, values, null, null) == 0) {
            val resultUri = contentResolver.insert(uri, values)
            Log.v(TAG, "Inserted at " + resultUri)
        } else {
            Log.v(TAG, "Updated at " + uri)
        }
    }

    protected fun query(id: U): T {
        Preconditions.checkNotNull(id, "Id cannot be null.")

        return query(getUriForKey(id))
    }

    protected fun query(uri: Uri): T {
        Preconditions.checkNotNull(uri, "URI cannot be null.")

        val cursor = contentResolver.query(uri, databaseContract.getProjection(), null, null, null)
        var value: T = null
        if (cursor != null) {
            value = databaseContract.read(cursor)
            cursor.close()
        }
        if (value == null) {
            Log.v(TAG, "Could not find with id: " + uri)
        }
        return value
    }

    protected fun getContentValuesForItem(item: T): ContentValues {
        return databaseContract.getContentValuesForItem(item)
    }

    public fun getUriForKey(id: U): Uri {
        Preconditions.checkNotNull(id, "Id cannot be null.")

        return Uri.withAppendedPath(getContentUri(), id.toString())
    }

    protected abstract fun getIdFor(item: T): U

    protected abstract fun getContentUri(): Uri

    companion object {
        private val TAG = javaClass<ContentProviderStoreBase<Any, Any>>().getSimpleName()

        private fun createHandler(name: String): Handler {
            Preconditions.checkNotNull(name, "Handler Name cannot be null.")

            val handlerThread = HandlerThread(name)
            handlerThread.start()
            return Handler(handlerThread.getLooper())
        }
    }
}
