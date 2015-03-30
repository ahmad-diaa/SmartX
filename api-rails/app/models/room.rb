class Room < ActiveRecord::Base
	belongs_to :user
	has_many :devices,dependent: :destroy
end
