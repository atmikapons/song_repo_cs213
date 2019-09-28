package view;

import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class Controller {

	@FXML	ListView<String> listView;
	@FXML	Label thisTitle;
	@FXML	Label thisArtist;
	@FXML	Label thisAlbum;
	@FXML	Label thisYear;
	@FXML	TextField editTitle;
	@FXML	TextField editArtist;
	@FXML	TextField editAlbum;
	@FXML	TextField editYear;
	
	//obsList can only hold Strings, since listView
	//is only capable of displaying Strings.
	//songList exists to keep track of all other song details
	// indices must match between listView, obsList, and songList
	// in order to make accessing data easy for us!
	private ObservableList<String> obsList;     
	private List<Song> songList = new ArrayList<Song>();

	public void start(Stage mainStage) {                
	
		// a separate method to populate obsList
		// so that obsList can eventually be created from a text file
		populateObsList();
		
		listView.setItems(obsList); 
		
		// select the first item by default
	      listView.getSelectionModel().select(0);
	      updateSelectedSongDetails(mainStage);
	      
	      // sets listener - when item is selected, update corresponding text fields
	      listView
	        .getSelectionModel()
	        .selectedIndexProperty()
	        .addListener(
	           (obs, oldVal, newVal) -> 
	               updateSelectedSongDetails(mainStage));

	}
	
	private void populateObsList() {
		songList.add(new Song("CoolSong", "Atmika", "potato", "2019"));
		songList.add(new Song("CoolSong2", "Atmika2", "potato", "2019"));
		
		// make sure songs in songList are sorted before adding to obsList!
		
		List<String> songTitles = new ArrayList<String>();
		for(Song s : songList) {
			songTitles.add(s.toString());
		}
		
		obsList = FXCollections.observableArrayList(songTitles); 
	}
	
	private void updateSelectedSongDetails(Stage mainStage) {                
	      int index = listView.getSelectionModel().getSelectedIndex();
	      
	    //set texts in Song Details Box equal to fields of selected song
	      thisTitle.setText(songList.get(index).getTitle());
	      thisArtist.setText(songList.get(index).getArtist());
	      thisAlbum.setText(songList.get(index).getAlbum());
	      thisYear.setText(songList.get(index).getYear());
	      
	    //set texts fields in Edit Details Box equal to fields of selected song  
	      editTitle.setText(songList.get(index).getTitle());
	      editArtist.setText(songList.get(index).getArtist());
	      editAlbum.setText(songList.get(index).getAlbum());
	      editYear.setText(songList.get(index).getYear());
	      
	      mainStage.show();
	   }
	
	public class Song {
		private String title;
		private String artist;
		private String album;
		private String year;
		
		public Song(String title, String artist, String album, String year) {
			super();
			this.title = title;
			this.artist = artist;
			this.album = album;
			this.year = year;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getArtist() {
			return artist;
		}
		public void setArtist(String artist) {
			this.artist = artist;
		}
		public String getAlbum() {
			return album;
		}
		public void setAlbum(String album) {
			this.album = album;
		}
		public String getYear() {
			return year;
		}
		public void setYear(String year) {
			this.year = year;
		}
		
		public String toString() {
			return title + " - " + artist;
		}
		
		
	}


}


