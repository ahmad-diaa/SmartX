class Plug < ActiveRecord::Base
  self.primary_key = 'device_id'
  belongs_to :room
  belongs_to :user
def to_param
    device_id.parameterize
  end
end
