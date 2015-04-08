require 'test_helper'
class NotesControllerTest < ActionController::TestCase
  #This class is a test for the note controller

  #Tests that the controller creates successfully a note when is called with right parameters.
	test "#create Note" do
  	  post 'create', {user_id: '1', room_id: '1', device_device_id: '1',
      		note: {
        		body: 'Billy Blowers',
        		user_id: '1',
        		room_id: '1',
        		device_id: '1'
      		}
    	}
    	results = JSON.parse(response.body)
    	assert results['id'] > 0
  	end

    #Tests that the controller fails to create successfully a note when is called with empty body.
  	test "#create Note with invalid data" do
  	  post 'create', {user_id: '1', room_id: '1', device_device_id: '1',
      		note: {
        		body: '',
        		user_id: '1',
        		room_id: '1',
        		device_id: '1'
      		}
    	}
    	 results = JSON.parse(response.body)
   		 assert results.size > 0
  	end

  #Tests that the controller shows the note successfully when is called with right parameters/
	test "#show Note" do
    	newnote = notes(:note_one)
   		post 'show', { user_id: newnote.user_id ,room_id: newnote.room_id,device_device_id: newnote.device_id,id: newnote.id }
    	results = JSON.parse(response.body)
    	assert results['id'] == newnote.id
    	assert results['body'] == newnote.body
    	assert results['user_id'] == newnote.user_id
    	assert results['room_id'] == newnote.room_id
    	assert results['device_id'] == newnote.device_id
  	end

     #Tests that the controller shows all the noted of a device successfully when is called with right parameters.
  	test "#index notes" do
  		get 'index' ,{user_id: '1', room_id: '1', device_device_id: '1'}
  		results = JSON.parse(response.body)
  		assert results.size > 0
  	end
  	
end