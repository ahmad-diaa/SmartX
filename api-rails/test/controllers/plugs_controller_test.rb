require 'test_helper'

class PlugsControllerTest < ActionController::TestCase
  #Created by zamzamy on 1/5/2015
  # A test for the plug controller
# Tests that the controller create the device successfully when is called with right parameters/
	test "#create plug" do
	  	  post 'create', {user_id: '9', room_id: '9',plug_id: '123',
	      		plug: {
					name:'zamzamys tab',
	        		photo: 'phone',
	        		status: '0'
	        		}
	    	}
	    	results = JSON.parse(response.body)
	    	assert results['id'] > 0
	  	end

	test "#index plug" do
	get 'index' ,{user_id: '9', room_id: '9', plug_id: '123'}
  		results = JSON.parse(response.body)
  		assert results.size > 0
  	end

	test "#show plug" do
  	get 'show', {user_id: '9', room_id: '9', id: '123'}
    	results = JSON.parse(response.body)
    	assert results.size > 0 
  	end
  	
end