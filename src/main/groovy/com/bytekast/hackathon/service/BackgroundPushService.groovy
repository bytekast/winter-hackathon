package com.bytekast.hackathon.service

import groovy.util.logging.Slf4j
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
@Slf4j
class BackgroundPushService {

  private final DateTimeFormatter dateTimeFormatter =
      DateTimeFormatter.ofPattern("E yyyy MM dd HH:mm:ss")

  def closures = [:]

  @Async
  def registerPushCallback(k, c) {
    closures[k] = c
    log.info "Registered: ${k.toString()}"
  }

  @Async
  def deregisterPushCallback(k) {
    closures.remove(k)
    log.info "Deregistered: ${k.toString()}"
  }

  @Scheduled(fixedRate = 1000L)
  def push() {
    def toBeRemoved = []
    closures.each { key, closure ->
      try {
        // push local time to registered callback
        closure(LocalDateTime.now().format(dateTimeFormatter))
      } catch (e) {
        toBeRemoved << key
      }
    }
    closures.keySet().removeAll(toBeRemoved)
  }

  @Scheduled(fixedRate = 60000L)
  def report() {
    log.info "Registered Push Threads: ${closures.size()}"
  }
}
