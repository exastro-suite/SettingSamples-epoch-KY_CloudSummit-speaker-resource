/*   Copyright 2021 NEC Corporation
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package exastro.Exastro_Days_Tokyo.speaker_resource.controller.api.v1.form;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SpeakerForm {
	@JsonProperty("speaker_id")
	private int speakerId;
	
	@JsonProperty("speaker_name")
	private String speakerName;
	
	private String speakerProfile;
	
	private boolean deleteFlag;
	
	public SpeakerForm() {
	}

	public SpeakerForm(int speakerId, String speakerName) {
		this.speakerId = speakerId;
		this.speakerName = speakerName;
	}
	
	public SpeakerForm(int speakerId, String speakerName,String speakerProfile, boolean deleteFlag) {
		this.speakerId = speakerId;
		this.speakerName = speakerName;
		this.speakerProfile = speakerProfile;
		this.deleteFlag = deleteFlag;
	}
	
	public int getSpeakerId() {
		return speakerId;
	}
	public void setSpeakerId(int speakerId) {
		this.speakerId = speakerId;
	}
	
	public String getSpeakerName() {
		return speakerName;
	}
	public void setSpeakerName(String speakerName) {
		this.speakerName = speakerName;
	}
	public String getSpeakerProfile() {
		return speakerProfile;
	}
	public void setSpeakerProfile(String speakerProfile) {
		this.speakerProfile = speakerProfile;
	}
	public boolean isDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
}

