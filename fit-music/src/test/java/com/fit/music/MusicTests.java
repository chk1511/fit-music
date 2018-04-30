package com.fit.music;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.sql.Date;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fit.entity.User;
import com.fit.rest.MusicController;

@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan(basePackages = { "com.fit.*" })
//@ContextConfiguration(locations="/WEB-INF/dataSource-context.xml")
public class MusicTests {
	
	@InjectMocks
	private MusicController musicController;
	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(musicController).build();
	}
	
	@Test
	public void musicList() {
		try {
			mockMvc.perform(get("/music_list?page=1"))
				.andExpect(status().isOk())
				.andExpect(view().name("/music/musicList"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void recommendation() {
		try{
			
			MockHttpServletRequestBuilder builder = get("/recommendation?page=1")
					.sessionAttr("loginId", "user");
			
			mockMvc.perform(builder)
					.andExpect(status().isOk())
					.andExpect(view().name("/music/musicList"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	@Test
	public void musicWrite() {
		try {			
			mockMvc.perform(get("/music_write"))
				.andExpect(status().isOk())
				.andExpect(view().name("/music/musicWrite"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	@Test
	public void musicCreate() {
		try {
			
			String date = "2016-11-10";
			Date releaseDate = Date.valueOf(date);
			
			JSONObject json = new JSONObject();
			json.put("albumTitle", "instagram");
			json.put("genre", "R&B / Soul");
			json.put("period", "2010");
			json.put("preferenceTf", true);
			json.put("releaseDate", releaseDate);
			json.put("singer", "DEAN");
			json.put("singerType", "남성솔로");
			json.put("songTitle", "instagram");
			
			User user = new User();
			user.setId("dev");
			user.setName("dev");
			user.setPassword("dev");
			
			String fileDir = "/앨범이미지";
			String fileName = "Bad Boy.jpg";
			String fileFullPath = String.format("%s/%s", fileDir, fileName);
			
			File file = new File(fileFullPath);
			FileInputStream fis = new FileInputStream(file);
			byte[] data = Files.readAllBytes(file.toPath());
			
			MockMultipartFile mockFile = new MockMultipartFile("file", fileName, "multipart/form-data", fis);
			
			MockMultipartHttpServletRequestBuilder builder = fileUpload("/music_create").file(mockFile);
					builder.param("period", "2010");
					builder.param("preferenceTf", "true");
					builder.param("releaseDate", date);
					builder.param("singer", "DEAN");
					builder.param("singerType", "남성솔로");
					builder.param("songTitle", "instagram");
					builder.param("albumTitle", "instagram");
					builder.sessionAttr("loginId", user.getId());
			
			mockMvc.perform(builder)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("/music/musicWrite"));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	@Test
//	public void musicUpdate() {
//		try{
//			
//			String date = "2017-12-26";
//			Date releaseDate = Date.valueOf(date);
//			
////			Music input = new Music();
////			input.setAlbumTitle("instagram");
////			input.setGenre("R&B / Soul");
////			input.setImgPath("/img/music/instagram.jpg");
////			input.setPeriod("2010");
////			input.setPreferenceTf(true);
////			input.setReleaseDate(releaseDate);
////			input.setSinger("DEAN");
////			input.setSingerType("남성솔로");
////			input.setSongTitle("instagram");
//			
//			JSONObject json = new JSONObject();
//			json.put("albumTitle", "instagram");
//			json.put("genre", "R&B / Soul");
//			json.put("imgPath", "/img/music/instagram.jpg");
//			json.put("period", "2010");
//			json.put("preferenceTf", true);
//			json.put("releaseDate", releaseDate);
//			json.put("singer", "DEAN");
//			json.put("singerType", "남성솔로");
//			json.put("songTitle", "instagram");
//			json.put("id", "5aa1285e3e578503f479455d");
//			
//			MockHttpServletRequestBuilder builder = post("/music_update")
//			.contentType(MediaType.APPLICATION_JSON_VALUE)
//			.content(json.toString());
//			
//			mockMvc.perform(builder)
//			.andExpect(status().isOk());
//			
//		}catch(Exception e){
//			
//		}
//	}
}