package view;

import javafx.scene.control.TextField;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
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
	@FXML	TextField addTitle;
	@FXML	TextField addArtist;
	@FXML	TextField addAlbum;
	@FXML	TextField addYear;
	@FXML	Button add;
	@FXML	Button delete;
	@FXML	Button saveEdits;

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

	public void addSong(ActionEvent e) {
		Button b = (Button)e.getSource();

		// get song details entered by user in addTitle, addArtist, etc
		// if addTitle || addArtist are empty throw error dialogue
		if(addTitle.getText().isBlank() || addArtist.getText().isBlank()) {
			//throw error
		}
		else if(!duplicateSong(addTitle.getText(), addArtist.getText())) {
			// add song into songList and obsList, in sorted order
			Song newSong = new Song(addTitle.getText(), addArtist.getText(), addAlbum.getText(), addYear.getText());
			//add song into songList and sort 
			songList.add(newSong);
			//sort 
			//update obsList 

		}
		else {
			// throw error dialogue 
			Alert songExists = new Alert(AlertType.ERROR, "Song already exists", ButtonType.CANCEL);
			songExists.showAndWait();

		}


		//sort list after adding
		//songList.sort(Song.getTitle()); 

	}

	public void editSong(ActionEvent e) {
		Button b = (Button)e.getSource();


		int index = listView.getSelectionModel().getSelectedIndex();
		if(editTitle.getText().equalsIgnoreCase(songList.get(index).getTitle()) 
				&& editArtist.getText().equalsIgnoreCase(songList.get(index).getArtist())) {
			songList.get(index).setAlbum(editAlbum.getText());
			songList.get(index).setYear(editYear.getText());
		}
		else if(!duplicateSong(editTitle.getText(), editArtist.getText())) {
			// edit details to match entered fields
			String oldArtist = songList.get(index).getArtist();
			String oldTitle = songList.get(index).getTitle();
			
			songList.get(index).setArtist(editArtist.getText());
			songList.get(index).setTitle(editTitle.getText());
			songList.get(index).setAlbum(editAlbum.getText());
			songList.get(index).setYear(editYear.getText());
			
			// if title || artist changed, replace current string in obsList with new toString()
			if(oldArtist.equalsIgnoreCase(editArtist.getText()) && oldTitle.equalsIgnoreCase(editArtist.getText())){
				// how to populate/update list
			}
		}

		else {
			// throw error dialogue
			Alert cantEdit = new Alert(AlertType.ERROR, "This song already exists", ButtonType.CANCEL);
			cantEdit.showAndWait();
		}
   

	}

	/**
	 * Checks whether or not the song is already contained in songList
	 * @return false if unique song/artist combo does not exist in songList 
	 */
	private boolean duplicateSong(String title, String artist) {
		//check every song in list 	
		for (int index = 0; index < songList.size(); index++) {
			//compare title
			String currTitle = songList.get(index).getTitle();
			//compare artist
			String currArtist = songList.get(index).getArtist();
			//if both same, then don't add 
			if (currTitle.equalsIgnoreCase(title) && currArtist.equalsIgnoreCase(artist)) {
				return true;
			}			
		}
		return false;
	}
	
	public int getIndexInsert(String newSongString) {
		
		for(int i = 0; i < songList.size(); i++) {
			int place = songList.get(index).toString().compareToIgnoreCase(newSongString);
			if (place == -1) {
				if (i==0) {
					return 0;
				} else {
				return i-1;
				}
			} 
		}
		return songList.size();
	}

	public void deleteSong(ActionEvent e) {
		Button b = (Button)e.getSource();
		

		
		int index = listView.getSelectionModel().getSelectedIndex();
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		 
		alert.setTitle("Delete Song");
		alert.setHeaderText("Are you sure you want to delete this song?");
		
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		    // ... user chose OK
		} else {
		    // ... user chose CANCEL or closed the dialog
		}
		
	
	}
	
	//song lives here
	private class Song {
		private String title;
		private String artist;
		private String album;
		private String year;

		public Song(String title, String artist, String album, String year) {
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


