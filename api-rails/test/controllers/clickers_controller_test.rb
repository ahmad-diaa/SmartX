require 'test_helper'

class ClickersControllerTest < ActionController::TestCase
#Tests that the controller creats the clicker successfully when is called with right parameters/
test "#create Clicker" do
  	  post 'create', {user_id: '1', room_id: '1', device_device_id: '1',
      		clicker: {
        		command: 'TV',
        		user_id: '1',
        		room_id: '1',
        		device_id: '1'
      		}
    	}
    	results = JSON.parse(response.body)
    	assert results['id'] > 0
  	end

#Tests that the controller shows the note successfully when is called with right parameters/
	test "#show clicker" do
    	newClicker = clickers(:one)
   		post 'show', { user_id: newClicker.user_id ,room_id: newClicker.room_id,device_device_id: newClicker.device_id,id: newClicker.id }
    	results = JSON.parse(response.body)
    	assert results['id'] == newClicker.id
    	assert results['command'] == newClicker.command
    	assert results['user_id'] == newClicker.user_id
    	assert results['room_id'] == newClicker.room_id
    	assert results['device_id'] == newClicker.device_id
  	end

#Tests that the controller shows the clicker successfully when is called with right parameters/
	test "#clicker's index" do
    	newClicker = clickers(:one)
   		post 'show', { user_id: newClicker.user_id ,room_id: newClicker.room_id,device_device_id: newClicker.device_id,id: newClicker.id }
    	results = JSON.parse(response.body)
    	assert results['id'] == newClicker.id
    	assert results['command'] == newClicker.command
    	assert results['user_id'] == newClicker.user_id
    	assert results['room_id'] == newClicker.room_id
    	assert results['device_id'] == newClicker.device_id
  	end
  	#Tests that the controller shows all the clickers of a device successfully 
	test "#index clickers" do
  		get 'index' ,{user_id: '1', room_id: '1', device_device_id: '1'}
  		results = JSON.parse(response.body)
  		assert results.size > 0
  	end
end
