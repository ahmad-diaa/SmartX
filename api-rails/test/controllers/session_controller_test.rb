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
end