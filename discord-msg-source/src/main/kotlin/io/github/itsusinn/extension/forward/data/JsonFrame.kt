package io.github.itsusinn.extension.forward.data

import com.fasterxml.jackson.annotation.JsonRawValue
import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.JsonNode
import io.github.itsusinn.extension.jackson.asString
import io.vertx.core.buffer.Buffer
import io.vertx.core.json.JsonObject
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

interface Frame

data class FrameData(
   val code:Int,
   @JsonTypeInfo(
      use = JsonTypeInfo.Id.CLASS,
      include = JsonTypeInfo.As.PROPERTY,
      property = "_type"
   )
   val data:Frame,
)