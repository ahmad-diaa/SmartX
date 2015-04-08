require 'test_helper'
class NotesControllerTest < ActionController::TestCase
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
  	test "#index notes" do
  		get 'index' ,{user_id: '1', room_id: '1', device_device_id: '1'}
  		results = JSON.parse(response.body)
  		assert results.size > 0
  	end
  	
end