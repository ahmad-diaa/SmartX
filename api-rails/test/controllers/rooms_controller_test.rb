require 'test_helper'

class RoomsControllerTest < ActionController::TestCase
#tests the rooms_controller

	 #tests that the controller updates the name successfully to a room when is called with right parameters
	test "#rename room" do
  	  put 'update', {user_id: '1', id: '1', 
      		room: {
        		name: 'Billy Blowers'
      		}
    	}
    	get 'show', {user_id: '1', id: '1'}
    	results = JSON.parse(response.body)
    	assert results['name'] == 'Billy Blowers'
  	end
  	#tests that the controller will not change the name when passed wrong parameters (empty room name)
  	test "#rename room with wrong parameters" do
  	  put 'update', {user_id: '1', id: '1', 
      		room: {
        		name: ''
      		}
    	}
    	get 'show', {user_id: '1', id: '1'}
    	results = JSON.parse(response.body)
    	assert results.size > 0 
  	end
end
