require 'test_helper'

class SessionControllerTest < ActionController::TestCase
  test "authenticate with username" do
    pw = 'secret'
    larry = User.create!(name: 'larry', password: pw, password_confirmation: pw)
    post 'create', { name: larry.name, password: pw }
    results = JSON.parse(response.body)
    assert results['access_token'] =~ /\S{32}/
    assert results['user_id'] == larry.id
  end

  	test "#session's index" do

    	newSession = session(:joe)
   		post 'show', { name: newSession.name ,access_token: newSession.access_token}
    	assert results['name'] == newSession.name
    	assert results['access_token'] == newSession.access_token
    	
  	end
end