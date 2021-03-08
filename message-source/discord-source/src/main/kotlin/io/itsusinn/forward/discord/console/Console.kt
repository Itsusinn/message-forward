/**
 * Copyright 2020-2021 Itsusinn and contributors.
 *
 * Licensed under the GNU Lesser General Public License version 3,
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *       https://opensource.org/licenses/LGPL-3.0
 */

package io.itsusinn.forward.discord.console

import io.itsusinn.extension.thread.SingleThreadCoroutineScope
import kotlinx.coroutines.async
import java.util.concurrent.ConcurrentHashMap

typealias Handler = suspend Iterator<String>.() -> String?

object Console : SingleThreadCoroutineScope() {
   val handlers = ConcurrentHashMap<String, Handler>()
   var helpInfo = "help"

   /**
    * Non Blocking
    */
   fun startListen() = async {
      while (true) {
         readLine()?.split(" ")?.iterator()?.let {
            println(handleLine(it))
         }
      }
   }
   private suspend fun handleLine(line: Iterator<String>): String {
      try {
         return doHandleLine(line) ?: helpInfo
      } catch (e: Exception) {
         e.printStackTrace()
         return e.message ?: "Occur Exception ${e.javaClass.name}"
      }
   }
   private suspend fun doHandleLine(line: Iterator<String>): String? {
      if (!line.hasNext()) return null
      val handler = handlers[line.next()]

      if (handler != null)
         return handler.invoke(line)
      else
         return null
   }

   fun handle(prefix: String, info: String = "No help info", handler: Handler) {
      handlers[prefix] = handler
   }

   fun unregisterHandler(prefix: String) {
      handlers.remove(prefix)
   }
}
