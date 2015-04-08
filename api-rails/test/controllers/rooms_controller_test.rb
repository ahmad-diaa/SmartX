require 'test_helper'

class RoomsControllerTest < ActionController::TestCase
 #tests that the controller creates successfully a note when is called with right parameters
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
end
