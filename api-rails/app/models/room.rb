class Room < ActiveRecord::Base
	 self.primary_key = 'room_id'
	belongs_to :user
def to_param
    room_id.parameterize
  end
end