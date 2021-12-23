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

package exastro.Exastro_Days_Tokyo.speaker_resource.controller.api.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import exastro.Exastro_Days_Tokyo.speaker_resource.controller.api.v1.form.SpeakerForm;
import exastro.Exastro_Days_Tokyo.speaker_resource.service.SpeakerService;
import exastro.Exastro_Days_Tokyo.speaker_resource.service.dto.SpeakerDto;

@RestController
@RequestMapping("/api/v1/speaker")
public class SpeakerResourceController {
	
	@Autowired
	protected SpeakerService service;
	
	public SpeakerResourceController() {
	}
	
	@GetMapping("/{speakerId}")
	public SpeakerForm SpeakerDetail(@PathVariable(value = "speakerId") @Validated int speakerId) {
		
		//登壇者マスターから取得
		SpeakerForm SpeakerDetail = null;
		try {
			SpeakerDto e = service.getSpeakerDetail(speakerId);
			SpeakerDetail = new SpeakerForm(e.getSpeakerId(), e.getSpeakerName(), e.getSpeakerProfile());
		
		}
		catch(Exception e) {
			throw e;
		}
		
		return SpeakerDetail;
	}
	
	//登壇者IDListをもとに登壇者名を返す。
	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> speaker(@RequestParam(name = "speaker_id", required = false) List<Integer> speakerIdList) {
		List<String> speakerList = null;
		try {
			speakerList = service.getEventSpeakerList(speakerIdList);
		}
		catch(Exception e) {
			throw e;
		}
		return speakerList;
	}
}
