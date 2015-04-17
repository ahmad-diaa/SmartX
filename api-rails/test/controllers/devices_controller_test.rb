require 'test_helper'

class DevicesControllerTest < ActionController::TestCase
# Tests that the controller create the device successfully when is called with right parameters/
test "#create device" do
  	  post 'create', {user_id: '1', room_id: '1',device_id: '1',
      		device: {
      			name: 'TV',
        		status: '0',
        		user_id: '1',
        		room_id: '1',
        		device_id: '1'
      		}
    	}
    	results = JSON.parse(response.body)
    	assert results['device_id'] > '0'
  	end

# Tests that the controller shows the device successfully when is called with right parameters/
	test "#show " do
    	newDevice = devices(:one)
   		post 'show', { user_id: newDevice.user_id ,room_id: newDevice.room_id,device_id: newDevice.device_id}
    	results = JSON.parse(response.body)
    	assert results['device_id'] == newDevice.device_id
    	assert results['status'] == newDevice.status
    	assert results['user_id'] == newDevice.user_id
    	assert results['room_id'] == newDevice.room_id
  	end

# Tests that the controller shows the device successfully when is called with right parameters/
	test "#device's index" do
    	newDevice = devices(:one)
   		post 'show', { user_id: newDevice.user_id ,room_id: newDevice.room_id,device_id: newDevice.device_id}
    	results = JSON.parse(response.body)
    	assert results['status'] == newDevice.status
    	assert results['user_id'] == newDevice.user_id
    	assert results['room_id'] == newDevice.room_id
    	assert results['device_id'] == newDevice.device_id
  	end
#  Tests that the controller shows all the devices of a device successfully 
	test "#index device" do
  		get 'index' ,{user_id: '1', room_id: '1',device_id: '1'}
  		results = JSON.parse(response.body)
  		assert results.size > 0
  	end
end

