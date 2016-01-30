/**
 *  Copyright 2015 SmartThings
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 *  Garage Door Monitor
 *
 *  Author: CJ Infantino
 */
definition(
    name: "Garage Door Monitor",
    namespace: "cjinfantino",
    author: "CJ Infantino",
    description: "Monitor Garage Door and send push notification if it's been open too long",
    category: "Safety & Security",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Meta/garage_contact.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Meta/garage_contact@2x.png"
)

preferences {
	section() {
		input "tiltsensor", "capability.contactSensor", title: "Select Sensor:", required: true
	}
	section() {
		input "maxOpenTime", "number", title: "How Many Minutes?", required: true
	}
}

def installed()
{
	log.info "Installed with settings: ${settings}"
    initialize()
}

def updated()
{
	log.info "Updated with settings: ${settings}"
	unsubscribe()
    initialize()
}

def initialize() {
	subscribe(tiltsensor, "contact.open", contactHandler)
}

def contactHandler(evt) {
	log.info "contactHandler called: $evt"
    // send the push notification when maxOpenTime has been reached
    runIn((maxOpenTime * 60), "myPush")
}

def myPush() {
    log.info "sending push notification to app"
    sendPush("The ${tiltsensor.displayName} is open!")
}