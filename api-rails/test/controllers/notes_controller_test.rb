require 'test_helper'
test "#show" do
    newnote = notes(:newnote)
    post 'show', { id: newnote.id }
    results = JSON.parse(response.body)
    assert results['id'] == newnote.id
    assert results['body'] == newnote.body
  end
end