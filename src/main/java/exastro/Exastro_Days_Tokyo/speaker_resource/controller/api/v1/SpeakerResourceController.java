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
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import exastro.Exastro_Days_Tokyo.speaker_resource.controller.api.v1.form.SpeakerDetailForm;
import exastro.Exastro_Days_Tokyo.speaker_resource.controller.api.v1.form.SpeakerForm;
import exastro.Exastro_Days_Tokyo.speaker_resource.service.SpeakerService;
import exastro.Exastro_Days_Tokyo.speaker_resource.service.dto.SpeakerDetailDto;

@RestController
@RequestMapping("/api/v1/speaker")
public class SpeakerResourceController extends BaseSpeakerController{
	
	@Autowired
	protected SpeakerService service;
	
	public SpeakerResourceController() {
	}
	

	@GetMapping("")
	public List<SpeakerForm> speaker(@RequestParam(name = "speaker_id", required = false) List<Integer> speakerIdList) {
		
		logger.debug("method called. [ " + Thread.currentThread().getStackTrace()[1].getMethodName() + " ]");
		
		List<SpeakerForm> speakerList = null;
		
		try {
			if(speakerIdList == null || speakerIdList.size() == 0) {
				speakerList = service.getSpeakerList()
						.stream()
						.map(s -> new SpeakerForm(s.getSpeakerId(), s.getSpeakerName()))
						.collect(Collectors.toList());
				
			} else {
				speakerList = service.getEventSpeakerList(speakerIdList)
						.stream()
						.map(s -> new SpeakerForm(s.getSpeakerId(), s.getSpeakerName()))
						.collect(Collectors.toList());
			}
		}
		catch(Exception e) {
			logger.debug(e.getMessage(), e);
			throw e;
		}
		
		return speakerList;
	}
	
	//登壇者登録
	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public String registerEvent(@RequestBody SpeakerDetailForm ed) {
		logger.debug("method called. [ " + Thread.currentThread().getStackTrace()[1].getMethodName() + " ]");
		
		SpeakerDetailDto speakerDetail = null;
		String resultStr = null;
		try {
			speakerDetail = new SpeakerDetailDto(ed.getSpeakerName(), ed.getSpeakerProfile());
			resultStr = service.registerSpeaker(speakerDetail);
		
		}
		catch(Exception e) {
			logger.debug(e.getMessage(), e);
			throw e;
		}
		
		return resultStr;
	}
	
	//登壇者更新
	@PutMapping("/{speakerId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public String updateEvent(@PathVariable(value = "speakerId") @Validated int speakerId, @RequestBody SpeakerDetailForm ed) {
		logger.debug("method called. [ " + Thread.currentThread().getStackTrace()[1].getMethodName() + " ]");
		
		SpeakerDetailDto speakerDetailDetail = null;
		String resultStr = null;
		try {
			// does data exist ?
			SpeakerDetailDto checkEd = service.getSpeakerDetail(speakerId);
			if(checkEd == null) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found data.");
			}
			
			// validate data
			if(speakerId != ed.getSpeakerId()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data.");
			}
			
			// update
			speakerDetailDetail = new SpeakerDetailDto(ed.getSpeakerId(), ed.getSpeakerName(), ed.getSpeakerProfile());
			resultStr = service.updateSpeaker(speakerDetailDetail);
		
		}
		catch(Exception e) {
			logger.debug(e.getMessage(), e);
			throw e;
		}
		
		return resultStr;
	}
	
	//登壇者削除
	@DeleteMapping("/{speakerId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public String deleteEvent(@PathVariable(value = "speakerId") @Validated int speakerId) {
		
		String resultStr = null;
		try {
			// does data exist ?
			SpeakerDetailDto checkEd = service.getSpeakerDetail(speakerId);
			if(checkEd == null) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found data.");
			}
			
			// does data exist ?
			if(checkEd.isDeleteFlag()) {
				return "{\"result\":\"already deleted.\"}";
			}
			
			resultStr = service.deleteSpeaker(speakerId);
		
		}
		catch(Exception e) {
			logger.debug(e.getMessage(), e);
			throw e;
		}
		
		return resultStr;
	}
}
