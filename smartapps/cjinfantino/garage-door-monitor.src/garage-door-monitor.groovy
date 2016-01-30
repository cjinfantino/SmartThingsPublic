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
 *  Author: SmartThings
 */
definition(
    name: "Garage Door Monitor",
    namespace: "cjinfantino",
    author: "CJ Infantino",
    description: "Monitor your garage door and get a text message if it is open too long",
    category: "Safety & Security",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Meta/garage_contact.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Meta/garage_contact@2x.png"
)

preferences {
	section("When the garage door is open...") {
		input "tiltsensor", "capability.contactSensor", title: "Select Tile Sensor", required: true
	}
//	section("For too long...") {
//		input "maxOpenTime", "number", title: "How long"
//	}
//	section("Text me at (optional, sends a push notification if not specified)...") {
//        input("recipients", "contact", title: "Notify", description: "Send notifications to") {
//            input "phone", "phone", title: "Phone number?", required: false
//        }
//	}
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
    if (sendPush) {
    	log.info "sending push notification to app"
    	sendPush("The ${tiltsensor.displayName} is open!")
	}        
}