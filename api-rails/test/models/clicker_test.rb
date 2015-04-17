require 'test_helper'
class ClickerTest < ActiveSupport::TestCase
 	
 	test "#create Clicker" do
  	  post 'create', {user_id: '1', room_id: '1', device_device_id: '1',
      		clickers: {
        		command:'TV',
        		user_id: '1',
        		room_id: '1',
        		device_id:'1'
      		}
    	}
    	results = JSON.parse(response.body)
    	assert results['id'] > 0
    #	assert true
  	end

end
