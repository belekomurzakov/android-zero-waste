package omurzakov.zerowaste

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class ZeroWasteTestRunner : AndroidJUnitRunner() {

    override fun newApplication(
        classLoader: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(classLoader, ZeroWasteTestApp::class.java.name, context)
    }
}