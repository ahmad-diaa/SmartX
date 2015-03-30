class Device < ActiveRecord::Base
	self.primary_key = 'device_id'
	belongs_to :room
def to_param
    device_id.parameterize
  end
end
